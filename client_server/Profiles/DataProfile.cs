using System;
using AutoMapper;
using client_server.Entities;
using client_server.Models;

namespace client_server.Profiles
{
    public class DataProfile : Profile
    {
        public DataProfile()
        {
            CreateMap<User, EUser>();
            CreateMap<EUser, User>();
            CreateMap<Address, EAddress>();
            CreateMap<EAddress, Address>();
        }
    }
}
