package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.datasource.fake.FakeBookDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.Book;
import com.zikozee.graphql.generated.types.ReleaseHistory;
import com.zikozee.graphql.generated.types.ReleaseHistoryInput;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 16 April 2022
 */

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {

    @DgsQuery(field = DgsConstants.QUERY.AdditionalOnRequest)
    public String additionalOnRequest(@RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
                                      @RequestHeader(name = "mandatoryHeader") String mandatoryHeader,
                                      @RequestParam(name = "optionalParam", required = false) String optionalParam,
                                      @RequestParam(name = "mandatoryParam") String mandatoryParam){

        var sb = new StringBuilder();

        sb.append("Optional header : ").append(optionalHeader);
        sb.append(", ");
        sb.append("Mandatory header : ").append(mandatoryHeader);
        sb.append(", ");
        sb.append("Optional param : ").append(optionalParam);
        sb.append(", ");
        sb.append("Mandatory param : ").append(mandatoryParam);

        return sb.toString();
    }
}
