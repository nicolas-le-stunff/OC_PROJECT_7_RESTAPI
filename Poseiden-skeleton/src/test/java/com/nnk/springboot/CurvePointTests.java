package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurvePointTests {

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
		CurvePoint curve = new CurvePoint();
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(3);
		curve.setValue(4);
		
		CurvePoint curveUpdate = new CurvePoint();
		curve.setId(1);
		curve.setCurveId(2);
		curve.setTerm(5);
		curve.setValue(4);
		
	
	
		
	}
	/*@Test
	public void curvePointTest() {
		CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);



		// Update
		curvePoint.setCurveId(20);
		curvePoint = curvePointRepository.save(curvePoint);
		Assert.assertTrue(curvePoint.getCurveId() == 20);

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
