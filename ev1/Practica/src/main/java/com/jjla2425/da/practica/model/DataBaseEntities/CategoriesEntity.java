package com.jjla2425.da.practica.model.DataBaseEntities;

import jakarta.persistence.*;
import org.json.JSONObject;

import java.util.Objects;

@Entity
@Table(name = "categories", schema = "public", catalog = "OnlineMarket")
public class CategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "category_id")
    private int categoryId;
    @Basic
    @Column(name = "category_name")
    private String categoryName;

    public static CategoriesEntity JSONToCategory(JSONObject jsonObject )
    {
        CategoriesEntity category = new CategoriesEntity();
        category.setCategoryId(Integer.parseInt(jsonObject.optString("categoryId", "")));
        category.setCategoryName(jsonObject.optString("categoryName", ""));
        return category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesEntity that = (CategoriesEntity) o;
        return categoryId == that.categoryId && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }
}
