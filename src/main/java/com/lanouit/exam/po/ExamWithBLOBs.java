package com.lanouit.exam.po;

public class ExamWithBLOBs extends Exam {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam.analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    private String analysis;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exam.review_analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    private String reviewAnalysis;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam.analysis
     *
     * @return the value of exam.analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public String getAnalysis() {
        return analysis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam.analysis
     *
     * @param analysis the value for exam.analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public void setAnalysis(String analysis) {
        this.analysis = analysis == null ? null : analysis.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exam.review_analysis
     *
     * @return the value of exam.review_analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public String getReviewAnalysis() {
        return reviewAnalysis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exam.review_analysis
     *
     * @param reviewAnalysis the value for exam.review_analysis
     *
     * @mbggenerated Mon Jun 05 10:56:44 CST 2017
     */
    public void setReviewAnalysis(String reviewAnalysis) {
        this.reviewAnalysis = reviewAnalysis == null ? null : reviewAnalysis.trim();
    }
}