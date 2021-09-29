using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Entities;
using client_server.Models;

namespace client_server.Services.Interfaces {
    public interface IUserService
    {
        Task<List<EUser>> All();
        Task<EUser> Get(int Id);
        Task<EUser> Create(User user);
        Task<EUser> Update(int Id, User user);
        Task<bool> Delete(int Id);
    }
}