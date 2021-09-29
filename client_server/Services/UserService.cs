using System.Collections.Generic;
using System.Threading.Tasks;
using AutoMapper;
using client_server.Entities;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace client_server.Services
{
    public class UserService : IUserService
    {
        private readonly DataContext _dataContext;
        private readonly IMapper _mapper;

        public UserService(DataContext dataContext, IMapper mapper)
        {
            _dataContext = dataContext;
            _mapper = mapper;
        }

        public async Task<List<EUser>> All()
        {
            var users = await _dataContext.Users.ToListAsync();
            return _mapper.Map<List<EUser>>(users);
        }

        public async Task<EUser> Create(User user)
        {
            user.CreatedDate = System.DateTime.Now;
            _dataContext.Users.Add(user);
            await _dataContext.SaveChangesAsync();
            return _mapper.Map<EUser>(user);
        }

        public async Task<bool> Delete(int Id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<EUser> Get(int Id)
        {
            var user = await _dataContext.Users.FindAsync(Id);
            return _mapper.Map<EUser>(user);
        }

        public async Task<EUser> Update(int Id, User user)
        {
            throw new System.NotImplementedException();
        }
    }
}