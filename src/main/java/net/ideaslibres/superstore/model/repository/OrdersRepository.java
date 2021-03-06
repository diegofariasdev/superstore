package net.ideaslibres.superstore.model.repository;

import net.ideaslibres.superstore.model.dto.OrderDto;
import net.ideaslibres.superstore.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.Optional;

public interface OrdersRepository extends PagingAndSortingRepository<OrderDto, Long> {
    Page<OrderDto> findAllByUser(UserDto user, Pageable pageable);
    Page<OrderDto> findAllByCreationTimestampBetween(Date startDate, Date endDate, Pageable pageable);
    Page<OrderDto> findAllByUserAndCreationTimestampBetween(UserDto user, Date startDate, Date endDate, Pageable pageable);
    Optional<OrderDto> findByCode(String code);
}
