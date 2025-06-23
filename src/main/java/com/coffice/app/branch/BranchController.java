package com.coffice.app.branch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coffice.app.page.Pager;
import com.coffice.app.sales.MenuVO;
import com.coffice.app.sales.SalesService;
import com.coffice.app.sales.SalesVO;
import com.coffice.app.users.RegisterGroup;
import com.coffice.app.users.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/branch/*")
public class BranchController {
	
	@Value("${kakao.map.appkey}")
	private String appkey;
	@Value("${business.service.key}")
	private String servicekey;
	@Autowired
	private BranchService branchService;
	@Autowired
	private SalesService salesService;
	
	@ModelAttribute("appkey")
	public String getAppkey() {	
		return this.appkey;
	}
	
	@ModelAttribute("servicekey")
	public String getServicekey() {	
		return this.servicekey;
	}

	@GetMapping("map")
	public void map(Model model, Pager pager) throws Exception {
		List<BranchVO> list = branchService.getList(pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		
		List<BranchVO> notAddBranchList = branchService.notAddBranchList();
		model.addAttribute("notAddBranchList", notAddBranchList);
		
		List<BranchMasterVO> notAddBranchMasterList = branchService.notAddBranchMasterList();
		model.addAttribute("notAddBranchMasterList", notAddBranchMasterList);
		
		Long total = branchService.totalSales();
		model.addAttribute("total", total);
		
		model.addAttribute("kind", "지점 > 지점지도");
		model.addAttribute("branch", "map");
	}
	
	@GetMapping("add")
	public void add(Model model,  @ModelAttribute BranchVO branchVO) throws Exception {
		model.addAttribute("kind", "지점 > 지점등록");
		model.addAttribute("branch", "add");
	}
	
	@PostMapping("add")
	public String add(@Validated(RegisterGroup.class) @ModelAttribute BranchVO branchVO,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) throws Exception {
		if(branchService.branchNameCheck(branchVO, bindingResult)) {
			return "branch/add";
		}
		
		int result = branchService.add(branchVO);
		
		if(result > 0) {
			redirectAttributes.addFlashAttribute("msg", "지점등록이 완료되었습니다!");
			return "redirect:./map";
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
	public String masterAdd(Model model, @ModelAttribute BranchMasterVO branchMasterVO) throws Exception {
		List<BranchMasterVO> notRegisterBranchMaster = branchService.notRegisterBranchMaster();
		model.addAttribute("notRegisterBranchMaster", notRegisterBranchMaster);
		model.addAttribute("kind", "지점 > 점주등록");
		model.addAttribute("branch", "masterAdd");
		model.addAttribute("today", LocalDate.now());
		return "branch/masterAdd";
	}
	
	@PostMapping("masterAdd")
	public String masterAdd(@Validated(RegisterGroup.class) @ModelAttribute BranchMasterVO branchMasterVO,
							RedirectAttributes redirectAttributes, 
							BindingResult bindingResult,
							Model model) throws Exception {
		
		log.info("bm:{}",branchMasterVO);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("branchMasterVO", branchMasterVO);
			model.addAttribute("notRegisterBranchMaster", branchService.notRegisterBranchMaster());
			return "branch/masterAdd";
		}
		
		if(branchService.nameErrorCheck(branchMasterVO, bindingResult)) {
			model.addAttribute("notRegisterBranchMaster", branchService.notRegisterBranchMaster());
			return "branch/masterAdd";
		}
		
		branchService.masterAdd(branchMasterVO);
		redirectAttributes.addFlashAttribute("msg", "점주등록이 완료되었습니다!");
		return "redirect:./map";
	}
	
	@GetMapping("myBranch")
	public void myBranch(@AuthenticationPrincipal UserVO userVO, BranchVO branchVO, Model model, Pager pager) throws Exception {
		String userId = userVO.getUserId();
		userVO.setUserId(userId);
		
		branchVO.setUserVO(userVO);
		List<BranchVO> list = branchService.myBranch(branchVO, pager);
		model.addAttribute("list", list);
		model.addAttribute("pager", pager);
		log.info("my list:{}",list);
		
		Long totalSale = branchService.totalBranchSales(branchVO);
		model.addAttribute("total", totalSale);
		
		List<SalesVO> chart = branchService.getChartList(branchVO);
		model.addAttribute("chart", chart);
		
		List<MenuVO> menuList = salesService.menuList();
		model.addAttribute("menuList", menuList);
		
		model.addAttribute("kind", "지점 > my지점");
		model.addAttribute("branch", "myBranch");
	}
	
	@GetMapping("/api/excel/download/branch")
	public ResponseEntity<byte[]> downloadExcel() throws IOException  {
		List<BranchVO> list;
		try {
			list = branchService.getDownList();
			
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
				if(branch.isBranchStatus()== true) {
					dataRow.createCell(4).setCellValue("운영중");
				} else if (branch.isBranchStatus()== false) {
					dataRow.createCell(4).setCellValue("운영안함");
				}
				
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
	
	@GetMapping("/api/excel/download/sale")
	public ResponseEntity<byte[]> downloadSaleExcel(@AuthenticationPrincipal UserVO userVO, BranchVO branchVO, Pager pager) throws IOException  {
		List<BranchVO> list;
		try {
			String userId = userVO.getUserId();
			userVO.setUserId(userId);
			branchVO.setUserVO(userVO);
			
			list = branchService.myBranch(branchVO, pager);
			log.info("list:{}",list.size());
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("지점");
			
			CreationHelper creationHelper = workbook.getCreationHelper();
			CellStyle dataCellStyle = workbook.createCellStyle();
			dataCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));
			
			// 헤더 행 생성
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("매출번호");
			headerRow.createCell(1).setCellValue("수입/지출");
			headerRow.createCell(2).setCellValue("금액");
			headerRow.createCell(3).setCellValue("날짜");
			headerRow.createCell(4).setCellValue("수량");
			headerRow.createCell(5).setCellValue("메뉴이름");
			
			
			// 데이터 행 생성
			int rowNum = 1;
			for (BranchVO branch : list) {

				for(int i =0; i<branch.getSalesVO().size();i++) {
					Row dataRow = sheet.createRow(rowNum++);
					
					dataRow.createCell(0).setCellValue(branch.getSalesVO().get(i).getSalesId());
					if(branch.getSalesVO().get(i).isSalesType()== true){
						dataRow.createCell(1).setCellValue("수입");
					} else if (branch.getSalesVO().get(i).isSalesType()== false) {
						dataRow.createCell(1).setCellValue("지출");
					}
					dataRow.createCell(2).setCellValue(branch.getSalesVO().get(i).getSalesProfit());
					Cell dateCell = dataRow.createCell(3);
					Date saleDate = branch.getSalesVO().get(i).getSalesDate();
					dateCell.setCellValue(saleDate);
					dateCell.setCellStyle(dataCellStyle);
					dataRow.createCell(4).setCellValue(branch.getSalesVO().get(i).getSalesQuantity());
					if(branch.getSalesVO().get(i).getMenuVO() != null) {
						dataRow.createCell(5).setCellValue(branch.getSalesVO().get(i).getMenuVO().getMenuName());
					}else {
						dataRow.createCell(5).setCellValue(branch.getSalesVO().get(i).getIngredientsVO().getIngredientsName());
					}
				}

				
			}
			
			// 열 너비 자동 조정
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			
			// 엑셀 파일을 ByteArrayOutputStream에 작성
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			workbook.close();
			
			// HTTP 응답 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=sales.xlsx");
			headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			
			return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
