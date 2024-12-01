package com.eu.inpost.inposttest.service.dto.request;

import java.math.BigDecimal;

public class CartEntryDto {
   private String uuid;
   private  int  qty;
   private BigDecimal price;

   public CartEntryDto() {
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public int  getQty() {
      return qty;
   }

   public void setQty(int qty) {
      this.qty = qty;
   }

   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
      this.price = price;
   }
}
