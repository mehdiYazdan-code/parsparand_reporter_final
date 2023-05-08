package com.armaninvestment.parsparandreporter.mappers;

import com.armaninvestment.parsparandreporter.dtos.ReportItemDto;
import com.armaninvestment.parsparandreporter.entities.Customer;
import com.armaninvestment.parsparandreporter.entities.ReportItem;
import org.springframework.stereotype.Component;

@Component
public class ReportItemMapperImpl implements ReportItemMapper {
    public ReportItemMapperImpl() {
    }

    public ReportItem toEntity(ReportItemDto reportItemDto) {
        if (reportItemDto == null) {
            return null;
        } else {
            ReportItem reportItem = new ReportItem();
            reportItem.setCustomer(this.reportItemDtoToCustomer(reportItemDto));
            reportItem.setId(reportItemDto.getId());
            reportItem.setInventory_paper(reportItemDto.getInventory_paper());
            reportItem.setProductCode(reportItemDto.getProductCode());
            reportItem.setUnitPrice(reportItemDto.getUnitPrice());
            reportItem.setQuantity(reportItemDto.getQuantity());
            return reportItem;
        }
    }

    public ReportItemDto toDto(ReportItem reportItem) {
        if (reportItem == null) {
            return null;
        } else {
            ReportItemDto reportItemDto = new ReportItemDto();
            reportItemDto.setCustomerId(this.reportItemCustomerId(reportItem));
            reportItemDto.setId(reportItem.getId());
            reportItemDto.setInventory_paper(reportItem.getInventory_paper());
            reportItemDto.setProductCode(reportItem.getProductCode());
            reportItemDto.setUnitPrice(reportItem.getUnitPrice());
            reportItemDto.setQuantity(reportItem.getQuantity());
            return reportItemDto;
        }
    }

    public ReportItem updateEntityFromDto(ReportItemDto reportItemDto, ReportItem reportItem) {
        if (reportItemDto != null) {
            if (reportItem.getCustomer() == null) {
                reportItem.setCustomer(new Customer());
            }

            this.reportItemDtoToCustomer1(reportItemDto, reportItem.getCustomer());
            if (reportItemDto.getId() != null) {
                reportItem.setId(reportItemDto.getId());
            }

            if (reportItemDto.getInventory_paper() != null) {
                reportItem.setInventory_paper(reportItemDto.getInventory_paper());
            }

            if (reportItemDto.getProductCode() != null) {
                reportItem.setProductCode(reportItemDto.getProductCode());
            }

            if (reportItemDto.getUnitPrice() != null) {
                reportItem.setUnitPrice(reportItemDto.getUnitPrice());
            }

            if (reportItemDto.getQuantity() != null) {
                reportItem.setQuantity(reportItemDto.getQuantity());
            }

        }
        return reportItem;
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

    private Long reportItemCustomerId(ReportItem reportItem) {
        if (reportItem == null) {
            return null;
        } else {
            Customer customer = reportItem.getCustomer();
            if (customer == null) {
                return null;
            } else {
                Long id = customer.getId();
                return (id == null) ? null : id;
            }
        }
    }

    protected void reportItemDtoToCustomer1(ReportItemDto reportItemDto, Customer mappingTarget) {
        if (reportItemDto != null) {
            if (reportItemDto.getCustomerId() != null) {
                mappingTarget.setId(reportItemDto.getCustomerId());
            }

        }
    }
}
