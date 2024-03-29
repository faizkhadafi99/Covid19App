package com.adl.main.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.adl.main.model.AllCovidInfo;
import com.adl.main.model.FaqsModel;
import com.adl.main.model.MockPostModel;
import com.adl.main.model.PreventionModel;
import com.adl.main.repository.FaqsRepository;
import com.adl.main.repository.PreventionRepository;
import com.adl.main.services.CovidApiService;
import com.adl.main.services.MockPostService;
import com.adl.main.utility.FileUtility;

@Controller
public class MainController {
	@Autowired
	CovidApiService covidService;
	
	@Autowired
	MockPostService mockPostService;
	
	@Autowired
	PreventionRepository preventionRepo;
	
	@Autowired
	FaqsRepository faqsRepo;
	
	
	@GetMapping("/")
	public String index( @RequestParam(name ="country",defaultValue = "") String country, Model model) {
		
		AllCovidInfo covid = new AllCovidInfo();
		if(country.equalsIgnoreCase("")) {
			covid=covidService.getCovidInfo("world");
		}else {
			covid=covidService.getCovidInfo(country);
		}
		
		List <AllCovidInfo> lstInfo = covidService.getAllCovidInfo();
		model.addAttribute("allCovidInfo",lstInfo);
		model.addAttribute("covidSearch",covid);
		model.addAttribute("mockModel",new MockPostModel());

		return "index";
	}
	
	@PostMapping("/")
	public String inputModel(@ModelAttribute("mockModel") MockPostModel data) {
		MockPostModel response = mockPostService.inputModel(data);
		return "redirect:/";
	}
	
	@GetMapping("/countries")
	public String countries() {
		
		return "countries";
	}
	@GetMapping("/faqs")
	public String faqs(Model model) {
		List<FaqsModel> lstFaqs = faqsRepo.findAll();
		model.addAttribute("lstFaqs",lstFaqs);
		
		return "faqs";
	}
	
	@GetMapping("/prevention")
		public String prevention(Model model) {
			List<PreventionModel> lstPrevention = preventionRepo.findAll();
			model.addAttribute("lstPrevention",lstPrevention);

		return "prevention";
	}
	
	@GetMapping("/symptoms")
	public String symptoms() {
		
		return "symptoms";
	}

	@GetMapping("/faqs/input")
	public String getInputFaqs(Model model) {
		
		model.addAttribute("faqsModel", new FaqsModel());
		return "input_faqs";
	}
	
	@PostMapping("/faqs/input")
	public String inputFaqs(
			@ModelAttribute("faqsModel") FaqsModel data,
			Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String type = file.getContentType();
		if (type.equalsIgnoreCase("image/png") || type.equalsIgnoreCase("image/jpeg")) {
			String uploadDir = "faqs-image/";
			FileUtility.saveFile(file, uploadDir, fileName);
			data.setImage(fileName);
			this.faqsRepo.save(data);
		}else {
			System.out.println("Bukan format yang benar !");
		}
		
		return "redirect:/faqs/input";
	}
	
	@GetMapping("/prevention/input")
	public String getInputPrevention(Model model) {
		
		model.addAttribute("preventionModel", new PreventionModel());
		return "input_prevention";
	}
	
	@PostMapping("/prevention/input")
	public String inputPrevention(
			@ModelAttribute("preventionModel") PreventionModel data,
			Model model,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String type = file.getContentType();
		if (type.equalsIgnoreCase("image/png") || type.equalsIgnoreCase("image/jpeg")) {
			String uploadDir = "prevention-image/";
			FileUtility.saveFile(file, uploadDir, fileName);
			data.setImage(fileName);
			this.preventionRepo.save(data);
		}else {
			System.out.println("Bukan format yang benar !");
		}
		
		return "redirect:/prevention/input";
	}
	
	


}
