package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.datasource.fake.FakeBookDataSource;
import com.zikozee.graphql.datasource.fake.FakeHelloDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Book;
import com.zikozee.graphql.generated.types.Hello;
import com.zikozee.graphql.generated.types.ReleaseHistory;
import com.zikozee.graphql.generated.types.ReleaseHistoryInput;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@DgsComponent
public class FakeBookDataResolver {

    @DgsQuery(field = "books")
    public List<Book> booksWrittenBy(@InputArgument(name = "author") Optional<String> authorName) {
        if (authorName.isEmpty() || StringUtils.isBlank(authorName.get()))
            return FakeBookDataSource.BOOK_LIST;

        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(book -> StringUtils.containsIgnoreCase(
                        book.getAuthor().getName(), authorName.get()
                )).collect(Collectors.toList());
    }

    //todo info:  same as below

    //        @DgsData(
//                parentType = DgsConstants.QUERY_TYPE,
//                field = DgsConstants.QUERY.BooksByReleased
//        )
//    public List<Book> fetchBooksByReleased(@InputArgument(name = "releasedInput") ReleaseHistoryInput input) {
//                    var releaseInput = ReleaseHistoryInput.newBuilder()
//                .printedEdition(input.getPrintedEdition())
//                .year(input.getYear())
//                .build();

    @DgsQuery(field = "booksByReleased")
    public List<Book> fetchBooksByReleased(DataFetchingEnvironment dataFetchingEnvironment) {
        var releasedMap = (Map<String, Object>) dataFetchingEnvironment.getArgument("releasedInput");

        var releaseInput = ReleaseHistoryInput.newBuilder()
                .printedEdition((boolean) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.PrintedEdition))
                .year((int) releasedMap.get(DgsConstants.RELEASEHISTORYINPUT.Year))
                .build();

        return FakeBookDataSource.BOOK_LIST.stream()
                .filter(b -> this.matchReleaseHistory(releaseInput, b.getReleased()))
                .collect(Collectors.toList());
    }

    private boolean matchReleaseHistory(ReleaseHistoryInput input, ReleaseHistory releaseHistory) {
        return input.getPrintedEdition().equals(releaseHistory.getPrintedEdition())
                && input.getYear() == releaseHistory.getYear();
    }
}
