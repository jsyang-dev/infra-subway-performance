package nextstep.subway.station.application;

import nextstep.subway.map.application.PathCacheEvictEvent;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import nextstep.subway.station.dto.StationRequest;
import nextstep.subway.station.dto.StationResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationService {

    private final StationRepository stationRepository;
    private final ApplicationEventPublisher eventPublisher;

    public StationService(StationRepository stationRepository, ApplicationEventPublisher eventPublisher) {
        this.stationRepository = stationRepository;
        this.eventPublisher = eventPublisher;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
        Station persistStation = stationRepository.save(stationRequest.toStation());
        eventPublisher.publishEvent(new PathCacheEvictEvent());
        return StationResponse.of(persistStation);
    }

    @Transactional(readOnly = true)
    public List<StationResponse> findAllStations(long id, int pageSize) {
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        List<Station> stations = stationRepository.findAll(id, pageRequest);

        return stations
                .stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
        eventPublisher.publishEvent(new PathCacheEvictEvent());
    }

    @Transactional(readOnly = true)
    public Station findStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public Station findById(Long id) {
        return stationRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
