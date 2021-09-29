using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace client_server.Services
{
    public class UserService : IUserService
    {
        private readonly DataContext _dataContext;

        public UserService(DataContext dataContext)
        {
            _dataContext = dataContext;
        }

        public async Task<List<User>> All()
        {
            var users = await _dataContext.Users.ToListAsync();
            return users;
        }

        public async Task<User> Create(User user)
        {
            user.CreatedDate = System.DateTime.Now;
            _dataContext.Users.Add(user);
            await _dataContext.SaveChangesAsync();
            return user;
        }

        public async Task<bool> Delete(int Id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<User> Get(int Id)
        {
            var user = await _dataContext.Users.FindAsync(Id);
            return user;
        }

        public async Task<User> Update(int Id, User user)
        {
            throw new System.NotImplementedException();
        }
    }
}