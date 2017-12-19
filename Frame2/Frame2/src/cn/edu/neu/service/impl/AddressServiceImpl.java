package cn.edu.neu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.neu.mapper.AddressMapper;
import cn.edu.neu.model.Address;
import cn.edu.neu.service.AddressService;
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressMapper addressMapper;
	public List<Address> getAddressByUserId(int loginUserId) {
		// TODO Auto-generated method stub
		List<Address> addrs = addressMapper.findAddrByUserId(loginUserId);
		return addrs;
	}
	@Override
	public void addAddress(Address address) {
		// TODO Auto-generated method stub
		addressMapper.saveAddress(address);
	}
	@Override
	public void updateAddress(Address address) {
		// TODO Auto-generated method stub
		addressMapper.updateAddress(address);
	}
	@Override
	public void delAddress(String addrId) {
		// TODO Auto-generated method stub
		addressMapper.delAddress(addrId);
	}
	@Override
	public void setDefaultAddress(String addrId) {
		// TODO Auto-generated method stub
		addressMapper.unsetDefaultAddress();
		addressMapper.setDefaultAddress(addrId);
	}
	@Override
	public Address getAddressById(String addrId) {
		// TODO Auto-generated method stub
		Address address = addressMapper.findAddressById(addrId);
		return address;
	}

}


