package com.devsuperior.bds02.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {
	
	private final EventRepository eventRepository;
	private final CityRepository cityRepository;

	public EventService(EventRepository eventRepository, CityRepository cityRepository) {
		this.eventRepository = eventRepository;
		this.cityRepository = cityRepository;
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = eventRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setDate(dto.getDate());
			entity.setUrl(dto.getUrl());
			if (dto.getCityId() != null) {
				City city = cityRepository.getReferenceById(dto.getCityId());
				entity.setCity(city);
			}
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id - " + id + ": not found");
		}		
	}
}
