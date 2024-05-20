package xyz.khamim.slash.ecommerce.graphql.service.helper;

import xyz.khamim.slash.ecommerce.graphql.model.Product;

import java.util.Comparator;
import java.util.Objects;

public class ProductComparator {

  public static Comparator<Product> getProductPriceCreatedDateComparator(boolean reverse) {

    return (o1, o2) -> {
      if (Objects.equals(o1.getPrice(), o2.getPrice())) {
        return !reverse ? o1.getCreatedDate().compareTo(o2.getCreatedDate()) :
          o2.getCreatedDate().compareTo(o1.getCreatedDate());
      }

      return 0;
    };
  }

  public static Comparator<Product> getProductCreatedDatePriceComparator(boolean reverse) {

    return (o1, o2) -> {
      if (Objects.equals(o1.getCreatedDate(), o2.getCreatedDate())) {
        return !reverse ? o1.getPrice() - o2.getPrice() : o2.getPrice() - o1.getPrice();
      }

      return 0;
    };
  }
}
