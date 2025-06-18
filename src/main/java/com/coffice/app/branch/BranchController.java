package com.coffice.app.branch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coffice.app.page.Pager;
import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/branch/*")
public class BranchController {
	
	@Value("${kakao.map.appkey}")
	private String appkey;
	@Autowired
	private BranchService branchService;
	
	@ModelAttribute("appkey")
	public String getAppkey() {
		
		return this.appkey;
	}

	@GetMapping("map")
	public String map(Model model, Pager pager) throws Exception {
		List<BranchVO> list = branchService.getList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		List<BranchVO> notAddBranchList = branchService.notAddBranchList();
		model.addAttribute("notAddBranchList", notAddBranchList);
		
		List<BranchMasterVO> notAddBranchMasterList = branchService.notAddBranchMasterList();
		model.addAttribute("notAddBranchMasterList", notAddBranchMasterList);
		
		Long total = branchService.totalSales();
		model.addAttribute("total", total);
		
		return "branch/map";
	}
	
	@GetMapping("add")
	public String add() throws Exception {
		return "branch/add";
	}
	@PostMapping("add")
	public String add(BranchVO branchVO) throws Exception {
		int result = branchService.add(branchVO);
		
		
		if(result > 0) {
			return "redirect:/";
		}
		return "branch/add";
	}
	@PostMapping("updateUser")
	public String updateUser(BranchVO branchVO) throws Exception {
		log.info("b:{}",branchVO);
		BranchVO branchVO2 = new BranchVO();
		branchVO2.setBranchId(branchVO.getBranchId());
		branchVO2.setUserId(branchVO.getUserId());
		
		branchService.branchUpdate(branchVO2);
		
		return "redirect:./map";
	}
	
	@GetMapping("detail")
	@ResponseBody
	public BranchVO detail(BranchVO branchVO) throws Exception {
		return branchService.getDetail(branchVO);
		
	}
	
	@GetMapping("masterAdd")
	public String masterAdd(Model model) throws Exception {
		List<BranchMasterVO> notRegisterBranchMaster = branchService.notRegisterBranchMaster();
		model.addAttribute("notRegisterBranchMaster", notRegisterBranchMaster);
		log.info("nr:{}",notRegisterBranchMaster);
		return "branch/masterAdd";
	}
	
	@PostMapping("masterAdd")
	public String masterAdd(BranchMasterVO branchMasterVO) throws Exception {
		log.info("bm:{}",branchMasterVO);
		branchService.masterAdd(branchMasterVO);
		return "redirect:/";
	}
	
	@GetMapping("myBranch")
	public String myBranch(@AuthenticationPrincipal UserVO userVO, BranchVO branchVO, Model model) throws Exception {
		String userId = userVO.getUserId();
		userVO.setUserId(userId);
		
		branchVO.setUserVO(userVO);
		List<BranchVO> list = branchService.myBranch(branchVO);
		model.addAttribute("list", list);
		
		Long totalSale = branchService.totalBranchSales(branchVO);
		model.addAttribute("total", totalSale);
		
		List<SalesVO> chart = branchService.getChartList(branchVO);
		model.addAttribute("chart", chart);
		log.info("c:{}",chart);
		
		return "branch/myBranch";
	}
	
	@GetMapping("/api/excel/download")
	public ResponseEntity<byte[]> downloadExcel() throws IOException  {
		List<BranchVO> list;
		try {
			list = branchService.getDownList();
			log.info("list:{}",list);
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("지점");
			
			// 헤더 행 생성
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("지점번호");
			headerRow.createCell(1).setCellValue("지점이름");
			headerRow.createCell(2).setCellValue("지점주소");
			headerRow.createCell(3).setCellValue("우편번호");
			headerRow.createCell(4).setCellValue("운영상태");
			headerRow.createCell(5).setCellValue("점주");
			headerRow.createCell(6).setCellValue("사업자번호");
			
			
			// 데이터 행 생성
			int rowNum = 1;
			for (BranchVO branch : list) {
				
				Row dataRow = sheet.createRow(rowNum++);
				
				
				
				dataRow.createCell(0).setCellValue(branch.getBranchId());
				dataRow.createCell(1).setCellValue(branch.getBranchName());
				dataRow.createCell(2).setCellValue(branch.getBranchAddress());
				dataRow.createCell(3).setCellValue(branch.getBranchPostcode());
				dataRow.createCell(4).setCellValue(branch.isBranchStatus());
				if(branch.getUserVO()==null) {
					dataRow.createCell(5).setCellValue("");
				}else {
					dataRow.createCell(5).setCellValue(branch.getUserVO().getName());					
				}
				if(branch.getBranchMasterVO()==null) {
					dataRow.createCell(6).setCellValue("");
				}else {
					dataRow.createCell(6).setCellValue(branch.getBranchMasterVO().getContactNumber());					
				}
			}
			
			// 열 너비 자동 조정
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			
			// 엑셀 파일을 ByteArrayOutputStream에 작성
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			workbook.close();
			
			// HTTP 응답 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=branch.xlsx");
			headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			
			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
