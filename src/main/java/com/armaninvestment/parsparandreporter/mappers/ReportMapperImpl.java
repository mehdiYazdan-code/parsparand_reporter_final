package com.armaninvestment.parsparandreporter.mappers;

import com.armaninvestment.parsparandreporter.dtos.ReportDto;
import com.armaninvestment.parsparandreporter.dtos.ReportItemDto;
import com.armaninvestment.parsparandreporter.entities.Customer;
import com.armaninvestment.parsparandreporter.entities.Report;
import com.armaninvestment.parsparandreporter.entities.ReportItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Component
public class ReportMapperImpl implements ReportMapper{
    public ReportMapperImpl() {
    }

    public Report toEntity(ReportDto reportDto) {
        if (reportDto == null) {
            return null;
        } else {
            Report report = new Report();
            report.setId(reportDto.getId());
            report.setExplanation(reportDto.getExplanation());
            report.setDate(reportDto.getDate());
            report.setReportItems(this.reportItemDtoSetToReportItemSet(reportDto.getReportItems()));
            this.linkReportItems(report);
            return report;
        }
    }

    public ReportDto toDto(Report report) {
        if (report == null) {
            return null;
        } else {
            ReportDto reportDto = new ReportDto();
            reportDto.setId(report.getId());
            reportDto.setExplanation(report.getExplanation());
            reportDto.setDate(report.getDate());
            reportDto.setReportItems(this.reportItemSetToReportItemDtoSet(report.getReportItems()));
            return reportDto;
        }
    }

    public Report updateEntityFromDto(ReportDto reportDto, Report report) {
        if (reportDto != null) {
            if (reportDto.getId() != null) {
                report.setId(reportDto.getId());
            }

            if (reportDto.getExplanation() != null) {
                report.setExplanation(reportDto.getExplanation());
            }

            if (reportDto.getDate() != null) {
                report.setDate(reportDto.getDate());
            }

            List list;
            if (report.getReportItems() != null) {
                list = this.reportItemDtoSetToReportItemSet(reportDto.getReportItems());
                if (list != null) {
                    report.getReportItems().clear();
                    report.getReportItems().addAll(list);
                }
            } else {
                list = this.reportItemDtoSetToReportItemSet(reportDto.getReportItems());
                if (list != null) {
                    report.setReportItems(list);
                }
            }

            this.linkReportItems(report);
        }
        return report;
    }

    protected ReportItem reportItemDtoToReportItem(ReportItemDto reportItemDto) {
        if (reportItemDto == null) {
            return null;
        } else {
            ReportItem reportItem = new ReportItem();
            reportItem.setId(reportItemDto.getId());
            reportItem.setInventory_paper(reportItemDto.getInventory_paper());
            reportItem.setProductCode(reportItemDto.getProductCode());
            reportItem.setUnitPrice(reportItemDto.getUnitPrice());
            reportItem.setQuantity(reportItemDto.getQuantity());
            reportItem.setCustomer(this.reportItemDtoToCustomer(reportItemDto));
            return reportItem;
        }
    }
    protected Customer reportItemDtoToCustomer(ReportItemDto reportItemDto) {
        if (reportItemDto == null) {
            return null;
        } else {
            Customer customer = new Customer();
            customer.setId(reportItemDto.getCustomerId());
            return customer;
        }
    }

    protected List<ReportItem> reportItemDtoSetToReportItemSet(List<ReportItemDto> list) {
        if (list == null) {
            return null;
        } else {
            List<ReportItem> list1 = new ArrayList<>(Math.max((int)((float)list.size() / 0.75F) + 1, 16));

            for (ReportItemDto reportItemDto : list) {
                list1.add(this.reportItemDtoToReportItem(reportItemDto));
            }

            return list1;
        }
    }

    protected ReportItemDto reportItemToReportItemDto(ReportItem reportItem) {
        if (reportItem == null) {
            return null;
        } else {
            ReportItemDto reportItemDto = new ReportItemDto();
            reportItemDto.setId(reportItem.getId());
            reportItemDto.setInventory_paper(reportItem.getInventory_paper());
            reportItemDto.setProductCode(reportItem.getProductCode());
            reportItemDto.setUnitPrice(reportItem.getUnitPrice());
            reportItemDto.setQuantity(reportItem.getQuantity());
            reportItemDto.setCustomerId(reportItem.getCustomer().getId());
            return reportItemDto;
        }
    }

    protected List<ReportItemDto> reportItemSetToReportItemDtoSet(List<ReportItem> list) {
        if (list == null) {
            return null;
        } else {
            List<ReportItemDto> list1 = new ArrayList<>(Math.max((int)((float)list.size() / 0.75F) + 1, 16));

            for (ReportItem reportItem : list) {
                list1.add(this.reportItemToReportItemDto(reportItem));
            }

            return list1;
        }
    }

}
