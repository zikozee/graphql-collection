package com.zikozee.graphql.comonent.fake;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.zikozee.graphql.datasource.fake.FakeMobileAppDataSource;
import com.zikozee.graphql.generated.DgsConstants;
import com.zikozee.graphql.generated.types.MobileApp;
import com.zikozee.graphql.generated.types.MobileAppFilter;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Ezekiel Eromosei
 * @created: 17 April 2022
 */

@DgsComponent
public class FakeMobileAppDataResolver {

//    @DgsData(
//            parentType = DgsConstants.QUERY_TYPE,
//            field = DgsConstants.QUERY.MobileApps
//    )
//    public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter", collectionType = MobileAppFilter.class)
//                                             Optional<MobileAppFilter> filter) {


    @DgsQuery(field = "mobileApps")
    public List<MobileApp> getMobileApps(@InputArgument(collectionType = MobileAppFilter.class)
                                             Optional<MobileAppFilter> mobileAppFilter) {
        if (mobileAppFilter.isEmpty()) return FakeMobileAppDataSource.MOBILE_APP_LIST;

        return FakeMobileAppDataSource.MOBILE_APP_LIST.stream()
                .filter(mobileApp -> this.matchFilter(mobileAppFilter.get(), mobileApp))
                .collect(Collectors.toList());
    }

    private boolean matchFilter(MobileAppFilter mobileAppFilter, MobileApp mobileApp) {
        var isAppMatch = StringUtils.containsIgnoreCase(mobileApp.getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getName(), StringUtils.EMPTY))
                && StringUtils.containsIgnoreCase(mobileApp.getVersion(),
                StringUtils.defaultIfBlank(mobileAppFilter.getVersion(), StringUtils.EMPTY));

        if (!isAppMatch) return false;

        if (StringUtils.isNotBlank(mobileAppFilter.getPlatform())
                && !mobileApp.getPlatform().contains(mobileAppFilter.getPlatform().toLowerCase()))
            return false;

        return mobileAppFilter.getAuthor() == null || StringUtils.containsIgnoreCase(mobileApp.getAuthor().getName(),
                StringUtils.defaultIfBlank(mobileAppFilter.getAuthor().getName(), StringUtils.EMPTY));
    }
}
