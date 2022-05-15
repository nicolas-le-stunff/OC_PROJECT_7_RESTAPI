package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import javassist.NotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointServiceTests {

	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private CurvePointService curvePointService;
	
	private CurvePoint curve = new CurvePoint();
	private CurvePoint curve2 = new CurvePoint();

	@Before
	public void init() {
		
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(3);
		curve.setValue(4);

		curve2.setId(2);
		curve2.setCurveId(4);
		curve2.setTerm(6);
		curve2.setValue(4);
	}
	

	@Test
	public void createCurvePointTest() {
		Mockito.verify(curvePointRepository,Mockito.times(0)).save(any());
		
		curvePointService.createCurvePoint(curve);
		
		Mockito.verify(curvePointRepository,Mockito.times(1)).save(any());
		
	}
	
	@Test
	public void updateCurvePointTest()throws NotFoundException {	
		Mockito.when(curvePointRepository.existsById(1)).thenReturn(true);
		Mockito.when(curvePointRepository.getOne(1)).thenReturn(curve);
		
		
		CurvePoint curveUpdate = new CurvePoint();
		curveUpdate.setId(1);
		curveUpdate.setCurveId(2);
		curveUpdate.setTerm(5);
		curveUpdate.setValue(4);	
		
		
		curvePointService.updateCurvePoint(1, curveUpdate);
		CurvePoint curveTest = curvePointService.getCurvePointById(1);
			
		assertEquals(5,curveTest.getTerm(),0);
	}

	@Test(expected = NotFoundException.class)
	public void updateCurvePointTestThrowsError()throws NotFoundException {	
		Mockito.when(curvePointRepository.existsById(1)).thenReturn(true);
		Mockito.when(curvePointRepository.getOne(1)).thenReturn(curve);
		
		
		CurvePoint curveUpdate = new CurvePoint();
		curveUpdate.setId(1);
		curveUpdate.setCurveId(2);
		curveUpdate.setTerm(5);
		curveUpdate.setValue(4);	
		
		
		curvePointService.updateCurvePoint(5, curveUpdate);

	}
	
	@Test
	public void getAllCurvePointTest() {
	
		
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curve);
		curvePointList.add(curve2);
		
		Mockito.when(curvePointRepository.findAll()).thenReturn(curvePointList);
		
		curvePointService.createCurvePoint(curve);
		curvePointService.createCurvePoint(curve2);
		
		List<CurvePoint> allCurvePoint = curvePointService.getAllCurvePoint();	
		assertEquals(allCurvePoint.size(),2);
	}
	
	
	@Test
	public void deleteCurvePointTest() throws NotFoundException {
		Mockito.verify(curvePointRepository,Mockito.times(0)).save(any());
		
		curvePointService.createCurvePoint(curve);
		
		Mockito.verify(curvePointRepository,Mockito.times(1)).save(any());
		
		curvePointService.deleteCurvePoint(1);
		
		Mockito.verify(curvePointRepository,Mockito.times(1)).deleteById(any());

	}
	
	@Test(expected = NotFoundException.class)
	public void getCurvePointByIdNotExistTest() throws NotFoundException {
		
		assertEquals(curvePointService.getCurvePointById(1), 0);
		
	}
}