package com.neu.deliveryPlatform.repository;

import com.neu.deliveryPlatform.entity.Place;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author asiawu
 * @Date 2023-04-18 17:52
 * @Description:
 */
public interface PlaceRepository extends MongoRepository<Place, String> {
}
