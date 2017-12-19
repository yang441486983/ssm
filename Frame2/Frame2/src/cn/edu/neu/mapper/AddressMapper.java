package cn.edu.neu.mapper;

import java.util.List;

import cn.edu.neu.model.Address;

public interface AddressMapper {


	List<Address> findAddrByUserId(int loginUserId);

	void saveAddress(Address address);

	void updateAddress(Address address);

	void delAddress(String addrId);

	void unsetDefaultAddress();

	void setDefaultAddress(String addrId);

	Address findAddressById(String addrId);


}
