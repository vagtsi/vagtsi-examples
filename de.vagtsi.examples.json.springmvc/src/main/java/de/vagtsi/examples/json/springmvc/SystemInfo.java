package de.vagtsi.examples.json.springmvc;

import java.util.Date;

public class SystemInfo {
	private String javaVersion;
	private String javaVmName;
	private String osName;
	private String osVersion;
	private Date systemTime;
	public String getJavaVersion() {
		return javaVersion;
	}
	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}
	public String getJavaVmName() {
		return javaVmName;
	}
	public void setJavaVmName(String javaVmName) {
		this.javaVmName = javaVmName;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public Date getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}
}
