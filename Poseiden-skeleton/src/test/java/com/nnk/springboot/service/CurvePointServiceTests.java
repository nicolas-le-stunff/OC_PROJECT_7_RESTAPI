package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointServiceTests {

	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private CurvePointService curvePointService;

	@Test
	public void createCurvePointTest() {
		Mockito.verify(curvePointRepository,Mockito.times(0)).save(any());
		
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(3);
		curve.setValue(4);
		
		curvePointService.createCurvePoint(curve);
		
		Mockito.verify(curvePointRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateCurvePointTest() {	
		Mockito.when(curvePointRepository.existsById(1)).thenReturn(true);
		
		
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(3);
		curve.setValue(4);
		
		Mockito.when(curvePointRepository.getOne(1)).thenReturn(curve);
		
		
		CurvePoint curveUpdate = new CurvePoint();
		curveUpdate.setId(1);
		curveUpdate.setCurveId(2);
		curveUpdate.setTerm(5);
		curveUpdate.setValue(4);
		
		
		
		try {
			curvePointService.updateCurvePoint(1, curveUpdate);
			CurvePoint curveTest = curvePointService.getCurvePointById(1);
			
			assertEquals(5,curveTest.getTerm(),0);
			
			
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
	}
	
	@Test
	public void getAllCurvePointTest() {
		
	
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(3);
		curve.setValue(4);
		
		CurvePoint curve2 = new CurvePoint();
		curve2.setId(2);
		curve2.setCurveId(4);
		curve2.setTerm(6);
		curve2.setValue(4);
		
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curve);
		curvePointList.add(curve2);
		
		Mockito.when(curvePointRepository.findAll()).thenReturn(curvePointList);
		
		curvePointService.createCurvePoint(curve);
		curvePointService.createCurvePoint(curve2);
		
		List<CurvePoint> allCurvePoint = curvePointService.getAllCurvePoint();	
		assertEquals(allCurvePoint.size(),2);
	}
	
	/*@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);



		// Find
		List<CurvePoint> listResult = curvePointRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		Assert.assertFalse(curvePointList.isPresent());
	}*/

}
