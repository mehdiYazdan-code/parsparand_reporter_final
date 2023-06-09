package com.armaninvestment.parsparandreporter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "report_explanation")
    private String explanation;
    @Column(name = "report_date")
    private LocalDate date;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportItem> reportItems = new ArrayList<>();

    public void addItem(ReportItem reportItem){
        reportItems.add(reportItem);
        reportItem.setReport(this);
    }
    public void removeItem(ReportItem reportItem){
        reportItems.remove(reportItem);
        reportItem.setReport(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Report report = (Report) o;
        return getId() != null && Objects.equals(getId(), report.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
