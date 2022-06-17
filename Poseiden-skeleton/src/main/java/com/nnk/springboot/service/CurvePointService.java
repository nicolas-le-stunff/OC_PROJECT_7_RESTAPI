package com.nnk.springboot.service;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import javassist.NotFoundException;

@Service
public class CurvePointService {
	
	@Autowired
	private CurvePointRepository curveRepository;

	private Logger log = LoggerFactory.getLogger(CurvePointService.class);
	
	
	/**
	 * Create a new CurvePoint
	 * @param curvePoint
	 * @return
	 */
	public CurvePoint createCurvePoint(CurvePoint curvePoint) {
		log.info("Create new Curve Point");
		CurvePoint newcurvePoint = new CurvePoint();
		newcurvePoint.setTerm(curvePoint.getTerm());
		newcurvePoint.setValue(curvePoint.getValue());
		newcurvePoint.setCurveId(curvePoint.getCurveId());
		return curveRepository.save(newcurvePoint);
	
	}
	
	/**
	 * Get all CurvePoint
	 * @return list of all CurvePoint
	 */
	public List<CurvePoint> getAllCurvePoint(){
		log.info("Get all Curve point");
		return curveRepository.findAll(); 
	}
	
	
	/**
	 * Delete CurvePoint by ID
	 * @param id
	 */
	@Transactional
	public void deleteCurvePoint (Integer id) {
		log.info("Delete curve point : "+id);
		curveRepository.deleteById(id);
	}
	
	/**
	 * Get CurvePoint by ID
	 * @param id
	 * @return
	 * @throws NotFoundException
	 */
	public CurvePoint getCurvePointById(Integer id) throws NotFoundException {
		log.info("Get curvePoint ID : "+id);
		if(curveRepository.existsById(id)) {
			return curveRepository.getOne(id);
		}
		log.error("curvePoint Id : "+id+" not exist");
		throw new NotFoundException("curvePoint Id : "+id+" not exist");
	}

	/**
	 * Update curvePoint
	 * @param id
	 * @param curvePoint
	 * @return
	 * @throws NotFoundException
	 */
	@Transactional
	public @Valid CurvePoint updateCurvePoint(Integer id, @Valid CurvePoint curvePoint) throws NotFoundException {
		log.info("Get a curvePoint by ID");
		if(curveRepository.existsById(id)) {
			CurvePoint curvePointUpdated = curveRepository.getOne(id);
			curvePointUpdated.setCurveId(curvePoint.getCurveId());
			curvePointUpdated.setTerm(curvePoint.getTerm());
			curvePointUpdated.setValue(curvePoint.getValue());
			log.info("Curve point updated");
			return curveRepository.save(curvePoint);
		}
		log.error("curvePoint Id : "+id+" not exist");
		throw new NotFoundException("curvePoint Id : "+id+" not exist");
	}
	


}
