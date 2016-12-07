package com.cms.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import com.cms.dto.StatisticStaffPointDTO;

import com.vfw5.base.model.BaseFWModel;

/**
* @author QuyenDM
* @version 1.0
* @since 06/12/2016 23:05:01
*/
@Entity
@Table(name = "STATISTIC_STAFF_POINT")
public class StatisticStaffPoint extends BaseFWModel {

 //Fields
   private Long id;
   private Long staffId;
   private String staffCode;
   private String staffName;
   private Long numberData;
   private Long numberCall;
   private Long w1Point;
   private Long w2Point;
   private Long w3Point;
   private Long w4Point;
   private Long w5Point;
   private Long monthPoint;
   private Long cancelPoint;
   private Long totalPoint;
  public StatisticStaffPoint() {
      setColId("id");
      setColName("staffName");
  }
      public StatisticStaffPoint(Long id) {
      this.id = id;
      }
     public StatisticStaffPoint(Long id,Long staffId,String staffCode,String staffName,Long numberData,Long numberCall,Long w1Point,Long w2Point,Long w3Point,Long w4Point,Long w5Point,Long monthPoint,Long cancelPoint,Long totalPoint){
       this.id = id;
       this.staffId = staffId;
       this.staffCode = staffCode;
       this.staffName = staffName;
       this.numberData = numberData;
       this.numberCall = numberCall;
       this.w1Point = w1Point;
       this.w2Point = w2Point;
       this.w3Point = w3Point;
       this.w4Point = w4Point;
       this.w5Point = w5Point;
       this.monthPoint = monthPoint;
       this.cancelPoint = cancelPoint;
       this.totalPoint = totalPoint;
   }
    //Getters and Setters
   @Id @GeneratedValue(generator="sequence")
   @GenericGenerator(name="sequence", strategy = "sequence",
            parameters = {
           @Parameter(name="sequence", value="STATISTIC_STAFF_POINT")
       }
   )
   @Column(name = "ID", nullable = false)
   public Long getId() {
       return this.id;
   }
   public void setId(final Long id) {
       this.id=id;
   }
   @Column(name = "STAFF_ID")
   public Long getStaffId() {
       return this.staffId;
   }
   public void setStaffId(final Long staffId) {
       this.staffId=staffId;
   }
   @Column(name = "STAFF_CODE")
   public String getStaffCode() {
       return this.staffCode;
   }
   public void setStaffCode(final String staffCode) {
       this.staffCode=staffCode;
   }
   @Column(name = "STAFF_NAME")
   public String getStaffName() {
       return this.staffName;
   }
   public void setStaffName(final String staffName) {
       this.staffName=staffName;
   }
   @Column(name = "NUMBER_DATA")
   public Long getNumberData() {
       return this.numberData;
   }
   public void setNumberData(final Long numberData) {
       this.numberData=numberData;
   }
   @Column(name = "NUMBER_CALL")
   public Long getNumberCall() {
       return this.numberCall;
   }
   public void setNumberCall(final Long numberCall) {
       this.numberCall=numberCall;
   }
   @Column(name = "W1_POINT")
   public Long getW1Point() {
       return this.w1Point;
   }
   public void setW1Point(final Long w1Point) {
       this.w1Point=w1Point;
   }
   @Column(name = "W2_POINT")
   public Long getW2Point() {
       return this.w2Point;
   }
   public void setW2Point(final Long w2Point) {
       this.w2Point=w2Point;
   }
   @Column(name = "W3_POINT")
   public Long getW3Point() {
       return this.w3Point;
   }
   public void setW3Point(final Long w3Point) {
       this.w3Point=w3Point;
   }
   @Column(name = "W4_POINT")
   public Long getW4Point() {
       return this.w4Point;
   }
   public void setW4Point(final Long w4Point) {
       this.w4Point=w4Point;
   }
   @Column(name = "W5_POINT")
   public Long getW5Point() {
       return this.w5Point;
   }
   public void setW5Point(final Long w5Point) {
       this.w5Point=w5Point;
   }
   @Column(name = "MONTH_POINT")
   public Long getMonthPoint() {
       return this.monthPoint;
   }
   public void setMonthPoint(final Long monthPoint) {
       this.monthPoint=monthPoint;
   }
   @Column(name = "CANCEL_POINT")
   public Long getCancelPoint() {
       return this.cancelPoint;
   }
   public void setCancelPoint(final Long cancelPoint) {
       this.cancelPoint=cancelPoint;
   }
   @Column(name = "TOTAL_POINT")
   public Long getTotalPoint() {
       return this.totalPoint;
   }
   public void setTotalPoint(final Long totalPoint) {
       this.totalPoint=totalPoint;
   }

   @Override
   public StatisticStaffPointDTO toDTO() {
       StatisticStaffPointDTO dto = new StatisticStaffPointDTO(
        id== null ? null : id.toString(),
        staffId== null ? null : staffId.toString(),
        staffCode,
        staffName,
        numberData== null ? null : numberData.toString(),
        numberCall== null ? null : numberCall.toString(),
        w1Point== null ? null : w1Point.toString(),
        w2Point== null ? null : w2Point.toString(),
        w3Point== null ? null : w3Point.toString(),
        w4Point== null ? null : w4Point.toString(),
        w5Point== null ? null : w5Point.toString(),
        monthPoint== null ? null : monthPoint.toString(),
        cancelPoint== null ? null : cancelPoint.toString(),
        totalPoint== null ? null : totalPoint.toString()
       );
       return dto;
   }
}
