package com.lanouit.exam.po;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;

import com.lanouit.exam.validator.group.UserDeleteGroup;
import com.lanouit.exam.validator.group.UserLoginGroup;
import com.lanouit.exam.validator.group.UserRegisterGroup;
import com.lanouit.exam.validator.group.UserUpdateGroup;
import com.lanouit.exam.validator.group.UserUpdatePasswordGroup;

public class User {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.id
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Null(groups = { UserUpdateGroup.class })
	@NotNull(groups = {UserDeleteGroup.class})
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_name
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Size(min = 2, max = 20, groups = { UserRegisterGroup.class,
			UserUpdateGroup.class })
	private String userName;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_account
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Email(message = "邮箱格式", groups = { UserLoginGroup.class })
	@Null(groups = { UserUpdateGroup.class, UserUpdatePasswordGroup.class })
	private String userAccount;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_password
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Size(min = 6, max = 20, message = "密码长度为6-20位", groups = {
			UserRegisterGroup.class, UserLoginGroup.class,
			UserUpdatePasswordGroup.class })
	private String userPassword;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_mail
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Email(message = "邮箱格式不正确", groups = UserRegisterGroup.class)
	@Null(groups = { UserUpdateGroup.class })
	private String userMail;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_position
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@NotNull(message = "职业不能为空", groups = { UserRegisterGroup.class,
			UserUpdateGroup.class })
	private Integer userPosition;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_type
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Null(groups = { UserUpdateGroup.class })
	private Integer userType;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_createtime
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Null(groups = { UserUpdateGroup.class })
	private Date userCreatetime;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_education
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	private String userEducation;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_sex
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Range(min = 0, max = 2, message = "请选择正确的性别", groups = { UserUpdateGroup.class })
	private Integer userSex;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_status
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Null(groups = { UserUpdateGroup.class })
	private Integer userStatus;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_token
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	@Null(groups = { UserUpdateGroup.class })
	private String userToken;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column user.user_pic
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	private String userPic;

	/**
	 * 新密码，用于密码修改时候与旧密码匹配用
	 * 
	 * @author Ken
	 */
	@Size(min = 6, max = 20, message = "密码长度为6-20位", groups = { UserUpdatePasswordGroup.class })
	private String newUserPassword;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.id
	 *
	 * @return the value of user.id
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.id
	 *
	 * @param id
	 *            the value for user.id
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_name
	 *
	 * @return the value of user.user_name
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_name
	 *
	 * @param userName
	 *            the value for user.user_name
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_account
	 *
	 * @return the value of user.user_account
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserAccount() {
		return userAccount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_account
	 *
	 * @param userAccount
	 *            the value for user.user_account
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount == null ? null : userAccount.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_password
	 *
	 * @return the value of user.user_password
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_password
	 *
	 * @param userPassword
	 *            the value for user.user_password
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword == null ? null : userPassword.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_mail
	 *
	 * @return the value of user.user_mail
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_mail
	 *
	 * @param userMail
	 *            the value for user.user_mail
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail == null ? null : userMail.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_position
	 *
	 * @return the value of user.user_position
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Integer getUserPosition() {
		return userPosition;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_position
	 *
	 * @param userPosition
	 *            the value for user.user_position
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserPosition(Integer userPosition) {
		this.userPosition = userPosition;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_type
	 *
	 * @return the value of user.user_type
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_type
	 *
	 * @param userType
	 *            the value for user.user_type
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_createtime
	 *
	 * @return the value of user.user_createtime
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Date getUserCreatetime() {
		return userCreatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_createtime
	 *
	 * @param userCreatetime
	 *            the value for user.user_createtime
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserCreatetime(Date userCreatetime) {
		this.userCreatetime = userCreatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_education
	 *
	 * @return the value of user.user_education
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserEducation() {
		return userEducation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_education
	 *
	 * @param userEducation
	 *            the value for user.user_education
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserEducation(String userEducation) {
		this.userEducation = userEducation == null ? null : userEducation
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_sex
	 *
	 * @return the value of user.user_sex
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Integer getUserSex() {
		return userSex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_sex
	 *
	 * @param userSex
	 *            the value for user.user_sex
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_status
	 *
	 * @return the value of user.user_status
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public Integer getUserStatus() {
		return userStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_status
	 *
	 * @param userStatus
	 *            the value for user.user_status
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_token
	 *
	 * @return the value of user.user_token
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserToken() {
		return userToken;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_token
	 *
	 * @param userToken
	 *            the value for user.user_token
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserToken(String userToken) {
		this.userToken = userToken == null ? null : userToken.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column user.user_pic
	 *
	 * @return the value of user.user_pic
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public String getUserPic() {
		return userPic;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column user.user_pic
	 *
	 * @param userPic
	 *            the value for user.user_pic
	 *
	 * @mbggenerated Wed Jun 07 10:20:04 CST 2017
	 */
	public void setUserPic(String userPic) {
		this.userPic = userPic == null ? null : userPic.trim();
	}

	/**
	 * 
	 * @author Ken
	 * @return the value of this newUserPassword
	 */
	public String getNewUserPassword() {
		return newUserPassword;
	}

	/**
	 * 
	 * @author Ken
	 * @param newUserPassword
	 */
	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}

	/**
	 * 
	 * @author Ken
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userAccount="
				+ userAccount + ", userPassword=" + userPassword
				+ ", userMail=" + userMail + ", userPosition=" + userPosition
				+ ", userType=" + userType + ", userCreatetime="
				+ userCreatetime + ", userEducation=" + userEducation
				+ ", userSex=" + userSex + ", userStatus=" + userStatus
				+ ", userToken=" + userToken + ", userPic=" + userPic
				+ ", newUserPassword=" + newUserPassword + "]";
	}

}