package de.vagtsi.examples.json.springmvc.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.vagtsi.examples.json.springmvc.SystemInfo;
import de.vagtsi.examples.json.springmvc.SystemInformationService;

@Controller
@RequestMapping("/system")
public class SystemInformationServiceController implements SystemInformationService {

	@RequestMapping(value="/info", method = RequestMethod.GET)
	public @ResponseBody SystemInfo getSystemInfo() {
		SystemInfo info = new SystemInfo();
		info.setJavaVersion(System.getProperty("java.version"));
		info.setJavaVmName(System.getProperty("java.vm.name"));
		info.setOsName(System.getProperty("os.name"));
		info.setOsVersion(System.getProperty("os.version"));
		info.setSystemTime(new Date());
		return info;
	}

	@RequestMapping(value="/version", method = RequestMethod.GET)
	public @ResponseBody String getSystemVersion() {
		return "2.0.0.test";
	}
}
