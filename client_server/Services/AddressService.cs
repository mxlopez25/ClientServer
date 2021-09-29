using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using client_server.Entities;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace client_server.Services
{
    public class AddressService : IAddressService
    {
        private readonly DataContext _dataContext;
        private readonly IUserService _userService;
        private readonly IMapper _mapper;

        public AddressService(DataContext dataContext, IUserService userService, IMapper mapper)
        {
            _dataContext = dataContext;
            _userService = userService;
            _mapper = mapper;
        }

        public async Task<List<EAddress>> All()
        {
            var addresses = await _dataContext.Addresses.ToListAsync();
            return _mapper.Map<List<EAddress>>(addresses);
        }

        public async Task<EAddress> Create(Address address)
        {
            var user = await _userService.Get(address.UserId);
            if(user == null)
            {
                return null;
            }
            _dataContext.Addresses.Add(address);
            await _dataContext.SaveChangesAsync();
            return _mapper.Map<EAddress>(address);
        }

        public async Task<bool> Delete(int Id)
        {
            throw new NotImplementedException();
        }

        public async Task<EAddress> Get(int Id)
        {
            var address = await _dataContext.Addresses.FindAsync(Id);
            return _mapper.Map<EAddress>(address);
        }

        public async Task<List<EAddress>> GetByUserId(int Id)
        {
            var addresses = await _dataContext.Addresses.Where(a => a.UserId == Id).ToListAsync();
            return _mapper.Map<List<EAddress>>(addresses);
        }

        public async Task<EAddress> Update(int Id, Address address)
        {
            throw new NotImplementedException();
        }
    }
}
