package edu.austral.dissis.notifications;

import java.time.LocalDateTime;

public class PromotionNotification extends Notification {
  private final String promotionCode;
  private final double discountPercentage;
  private final LocalDateTime validUntil;

  public PromotionNotification(
      String content,
      NotificationPriority priority,
      String promotionCode,
      double discountPercentage,
      LocalDateTime validUntil) {
    super(content, NotificationType.PROMOTION, priority);
    this.promotionCode = promotionCode;
    this.discountPercentage = discountPercentage;
    this.validUntil = validUntil;
  }

  public String getPromotionCode() {
    return promotionCode;
  }

  public double getDiscountPercentage() {
    return discountPercentage;
  }

  public LocalDateTime getValidUntil() {
    return validUntil;
  }
}
