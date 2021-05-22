package com.project.securecloud.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.securecloud.entity.PersonEntity;
import com.project.securecloud.repository.FileRepository;
import com.project.securecloud.utills.Utills;

@Controller
public class SecureCloudController {

	@Autowired
	private FileRepository fileRepository;

	private static Logger logger = LoggerFactory.getLogger(SecureCloudController.class);

	@PostMapping("savexml")
	public String saveXmlFile(@RequestParam(name = "firstFile") MultipartFile firstFile,
			@RequestParam(name = "secondFile") MultipartFile secondFile, Model m) {

		try {

			List<Map<String, Object>> firstList = Utills.fetchDataFromXML(firstFile.getBytes());

			List<Map<String, Object>> secondList = Utills.fetchDataFromXML(secondFile.getBytes());
			firstList.forEach(first -> {

				Map<String, Object> map = secondList.stream()
						.filter(second -> second.get("name").equals(first.get("name"))).findAny().orElse(null);
				if (Objects.nonNull(map) && !map.isEmpty()) {
					first.putAll(map);
					logger.info("Final XML data = {}", first);
					fileRepository.saveData(new ObjectMapper().convertValue(first, PersonEntity.class));
				}
			});

		} catch (Exception e) {
			logger.error("Exception = {}", e.getMessage());
			m.addAttribute("message", "Data not saved");
		}
		m.addAttribute("message", "Data not saved");
		return "search";
	}

	@GetMapping("/search")
	public String searchData(@RequestParam(name = "q", required = false, defaultValue = " ") String q, Model m) {
		m.addAttribute("list", fileRepository.getDataBasedOnFilter(q));
		return "search";
	}

	@GetMapping("/")
	public String saveData(Model m) {
		m.addAttribute("message", "Welcome");
		return "savedata";
	}
}
