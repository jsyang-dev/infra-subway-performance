package nextstep.subway.map.application;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PathEventListener {

    private final MapService mapService;

    public PathEventListener(MapService mapService) {
        this.mapService = mapService;
    }

    @Async
    @EventListener
    public void evictCache(PathCacheEvictEvent pathCacheEvictEvent) {
        mapService.evictPathCache();
    }
}
