package com.lanouit.exam.po;

public class QuestionSubject {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question_subject.id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question_subject.question_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    private Integer questionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column question_subject.subject_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    private Integer subjectId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question_subject.id
     *
     * @return the value of question_subject.id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question_subject.id
     *
     * @param id the value for question_subject.id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question_subject.question_id
     *
     * @return the value of question_subject.question_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question_subject.question_id
     *
     * @param questionId the value for question_subject.question_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column question_subject.subject_id
     *
     * @return the value of question_subject.subject_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column question_subject.subject_id
     *
     * @param subjectId the value for question_subject.subject_id
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}