package cn.edu.neu.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.neu.model.Address;
import cn.edu.neu.service.AddressService;

@Controller
@RequestMapping("/appAddress")
public class AppAddressAction extends BaseAction {
	@Autowired
	private AddressService addressService;
	@ResponseBody
	@RequestMapping("/handleAddress")
	public String handleAddress(Address address){
		try{
			System.out.println("AddressAction:"+address.getAddrId());
			if(address.getAddrId()==0){
				address.setUserId(this.getLoginUserId());
				addressService.addAddress(address);
			}else{
				addressService.updateAddress(address);
			}
			return "foreard:/address/getMyAddress";
		}catch(Exception e){
			e.printStackTrace();
			this.addMessage("操作收货地址失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
	@ResponseBody
	@RequestMapping("/getMyAddress")
	public Map<String, List<Address>> getMyAdddress(){
		Map<String,List<Address>> m = new HashMap<>();
		List<Address> addrs = addressService.getAddressByUserId(this
				.getLoginUserId());
		m.put("address",addrs);
		return m;
		
		
	}
	@ResponseBody
	@RequestMapping("/setDefaultAddress")
	public Map<String,String> setDefaultAddress(@RequestParam String addrId){
		Map<String,String> m = new HashMap<String, String>();
		try{
			addressService.setDefaultAddress(addrId);
			m.put("setDefault", "success");
		}catch(Exception e){
			e.printStackTrace();
			m.put("setDefault", "failure");
		}
		return m;
	}
	@ResponseBody
	@RequestMapping("/delAddress")
	public String delAddressAjax(@RequestParam String addrId){
		try{
			addressService.delAddress(addrId);
			return "forward:/address/getMyAddressAjax";
		}catch (Exception e){
			e.printStackTrace();
			this.addMessage("删除收货地址失败");
			this.addRedirURL("返回", "@back");
			return EXECUTE_RESULT;
		}
	}
}
