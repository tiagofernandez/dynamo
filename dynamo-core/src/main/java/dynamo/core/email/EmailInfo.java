package dynamo.core.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * EmailInfo.
 * 
 * @author Tiago Fernandez
 * @since 0.1
 */
public class EmailInfo {

	private String body;
	private boolean html;
	private String from;
	private String subject;
	
	private Date creationDate = new Date();

	private List<String> to = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private List<String> bcc = new ArrayList<String>();
	private List<AttachmentInfo> attachments = new ArrayList<AttachmentInfo>();

	/**
	 * @return the attachments
	 */
	public List<AttachmentInfo> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<AttachmentInfo> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @param attachment to add
	 */
	public void addAttachment(AttachmentInfo attachment) {
		attachments.add(attachment);
	}

	/**
	 * @param attachment to remove
	 */
	public void removeAttachment(AttachmentInfo attachment) {
		attachments.remove(attachment);
	}

	/**
	 * @return the bcc
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 * @param isHtml true if body is HTML
	 */
	public void setBody(String body, boolean isHtml) {
		this.body = body;
		this.html = isHtml;
	}
	
	/**
	 * @return true if body is HTML
	 */
	public boolean isHtml() {
		return html;
	}

	/**
	 * @return the cc
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(List<String> cc) {
		this.cc.addAll(cc);
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(List<String> to) {
		this.to.addAll(to);
	}

	/**
	 * @param address to add
	 */
	public void addTo(String address) {
		if (EmailUtils.isValid(address)) {
			to.add(address.trim());
		}
	}

	/**
	 * @param address to add
	 */
	public void addCc(String address) {
		if (EmailUtils.isValid(address)) {
			cc.add(address.trim());
		}
	}

	/**
	 * @param address to add
	 */
	public void addBcc(String address) {
		if (EmailUtils.isValid(address)) {
			bcc.add(address.trim());
		}
	}

	/**
	 * @param address to remove
	 */
	public void removeTo(String address) {
		if (address != null) {
			to.remove(address.trim());
		}
	}

	/**
	 * @param address to remove
	 */
	public void removeCc(String address) {
		if (address != null) {
			cc.remove(address.trim());
		}
	}

	/**
	 * @param address to remove
	 */
	public void removeBcc(String address) {
		if (address != null) {
			bcc.remove(address.trim());
		}
	}

	/**
	 * @return all recipients (to, cc and bcc)
	 */
	public String getRecipients() {
		List<String> list = new ArrayList<String>();
		list.addAll(to);
		list.addAll(cc);
		list.addAll(bcc);
		return getRecipients(list);
	}

	/**
	 * @return the to recipients
	 */
	public String getToRecipients() {
		return getRecipients(to);
	}
	
	/**
	 * @return the cc recipients
	 */
	public String getCcRecipients() {
		return getRecipients(cc);
	}

	/**
	 * @return the bcc recipients
	 */
	public String getBccRecipients() {
		return getRecipients(bcc);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n====================================================");
		builder.append("\nDate: ").append(creationDate);
		builder.append("\nFrom: ").append(from);
		if (!to.isEmpty()) {
			builder.append("\nTo: ").append(getToRecipients());
		}
		if (!cc.isEmpty()) {
			builder.append("\nCC: ").append(getCcRecipients());
		}
		if (!bcc.isEmpty()) {
			builder.append("\nBCC: ").append(getBccRecipients());
		}
		builder.append("\nSubject: ").append(subject);
		if (attachments != null && !attachments.isEmpty()) {
			builder.append("\nAttachments: ").append(attachments.size());
		}
		builder.append("\n====================================================");
		return builder.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean equals = false;
		if (other instanceof EmailInfo) {
			EmailInfo castOther = (EmailInfo) other;
			equals = new EqualsBuilder()
				.append(this.getCreationDate(), castOther.getCreationDate())
				.append(this.getBody(), castOther.getBody())
				.append(this.isHtml(), castOther.isHtml())
				.append(this.getFrom(), castOther.getFrom())
				.append(this.getSubject(), castOther.getSubject())
				.append(this.getRecipients(), castOther.getRecipients())
				.isEquals();
		}
		return equals;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getCreationDate())
			.append(getBody())
			.append(isHtml())
			.append(getFrom())
			.append(getSubject())
			.append(getRecipients())
			.toHashCode();
	}

	private String getRecipients(List<String> list) {
		StringBuilder builder = new StringBuilder();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			builder.append(list.get(i));
			if (i < size - 1) {
				builder.append(", ");
			}
		}
		return builder.toString().trim();
	}

}