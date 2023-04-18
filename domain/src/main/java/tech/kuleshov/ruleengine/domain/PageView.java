package tech.kuleshov.ruleengine.domain;

import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
public class PageView<T> {
    private long total;
    private long pages;
    private long currentPage;
    private Collection<T> data;

    public static long calculateTotalPages(long totalRecords, long pageSize) {
        return (totalRecords + pageSize - 1) / pageSize;
    }
}
