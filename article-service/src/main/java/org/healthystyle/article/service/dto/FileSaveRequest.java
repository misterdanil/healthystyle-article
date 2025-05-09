package org.healthystyle.article.service.dto;

public class FileSaveRequest {
	private String root;
	private String relativePath;
	private byte[] bytes;
	private String correlationId;
	private String replyTo;

	public FileSaveRequest(String root, String relativePath, byte[] bytes) {
		super();
		this.root = root;
		this.relativePath = relativePath;
		this.bytes = bytes;
	}

	public FileSaveRequest(String root, String relativePath, byte[] bytes, String correlationId, String replyTo) {
		super();
		this.root = root;
		this.relativePath = relativePath;
		this.bytes = bytes;
		this.correlationId = correlationId;
		this.replyTo = replyTo;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}
