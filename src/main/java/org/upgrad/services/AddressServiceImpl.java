package org.upgrad.services;


import org.springframework.stereotype.Service;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.repositories.StateRepository;
import org.upgrad.models.Address;
import org.upgrad.models.States;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    private final StateRepository stateRepository;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository, StateRepository stateRepository){
        this.addressRepository = addressRepository;
        this.stateRepository = stateRepository;
    }


    @Override
    public Integer addAddress(Address address) { return addressRepository.addAddress(address.getFlatbuildNumber(), address.getLocality(), address.getCity() , address.getZipcode() , address.getState().getId()); }


    @Override
    public States checkValidState(Integer id) { return stateRepository.isValidState(id); }


    @Override
    public Integer countAddress(){
        return addressRepository.countAddress() ;
    }


    @Override
    public Integer addUserAddress(String temp, Integer user_id, Integer address_id) { return addressRepository.addUserAddress(temp, user_id, address_id); }


    @Override
    public Iterable<States> getAllStates() {
        return stateRepository.getAllStates();
    }


    @Override
    public Address getaddressById( Integer addressId) { return addressRepository.findAddressById(addressId) ;}


    @Override
    public Integer updateAddressById (String flat_build_num , String locality, String city, String zipcode , Integer state_id , Integer id)
    {
        return addressRepository.updateAddressById(flat_build_num,locality,city,zipcode,state_id,id);
    }


    @Override
    public Integer deleteAddressById (Integer id )
    {
        return addressRepository.deleteAddressById(id);
    }


    @Override
    public Integer deleteUserAddressById(Integer id) { return addressRepository.deleteUserAddressById(id); }


    @Override
    public Boolean getAddress(Integer addressId){
        if (addressRepository.findAddressById(addressId) == null )
            return false;
        else
            return true ;
    }


    @Override
    public Iterable<Address> getPermAddress(Integer userId)
    {
        List<Address> userList = new ArrayList<>();

        Iterable<Integer> permAddressIdList = addressRepository.getPermAdd(userId);

        System.out.println("Length of address list: "+permAddressIdList.toString());

        if( permAddressIdList.iterator().hasNext() )
        {
            for (Integer addressId: permAddressIdList ) {
                System.out.println("Details for Address id "+addressId);
                Address  add = addressRepository.findAddressById(addressId) ;
                States state = stateRepository.getStatebyId(add.getState().getId());

                Address resp = new Address(add.getId(),add.getFlatbuildNumber(), add.getLocality(), add.getCity(), add.getZipcode(), state);
                userList.add(resp);
            }
        }
        return userList;
    }
}
