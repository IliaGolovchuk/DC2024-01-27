﻿using AutoMapper;
using DC.DTO.RequestDTO;
using DC.DTO.ResponseDTO;
using DC.Exceptions;
using DC.Infrastructure.Validators;
using DC.Models;
using DC.Repositories.Interfaces;
using DC.Services.Interfaces;
using FluentValidation;
using System.Numerics;

namespace DC.Services.Impementations
{
	public class CreatorService(ICreatorRepository creatorRepository, 
		IMapper mapper, CreatorRequestDtoValidator validator) : ICreatorService
	{
		private readonly ICreatorRepository _creatorRepository = creatorRepository;
		private readonly IMapper _mapper = mapper;
		private readonly CreatorRequestDtoValidator _validator = validator;

		public async Task<IEnumerable<CreatorResponseDto>> GetCreatorsAsync()
		{
			var creators = await _creatorRepository.GetAllAsync();
			return _mapper.Map<IEnumerable<CreatorResponseDto>>(creators);
		}

		public async Task<CreatorResponseDto> GetCreatorByIdAsync(long id)
		{
			var creator = await _creatorRepository.GetByIdAsync(id) 
				?? throw new NotFoundException(ErrorMessages.CreatorNotFoundMessage(id));
			return _mapper.Map<CreatorResponseDto>(creator);
		}

		public async Task<CreatorResponseDto> CreateCreatorAsync(CreatorRequestDto creator)
		{
			_validator.ValidateAndThrow(creator);
			var creatorToCreate = _mapper.Map<Creator>(creator);
			var createdCreator = await _creatorRepository.CreateAsync(creatorToCreate);
			return _mapper.Map<CreatorResponseDto>(createdCreator);
		}
		public async Task<CreatorResponseDto> UpdateCreatorAsync(CreatorRequestDto creator)
		{
			_validator.ValidateAndThrow(creator);
			var creatorToUpdate = _mapper.Map<Creator>(creator);
			var updatedCreator = await _creatorRepository.UpdateAsync(creatorToUpdate)
				?? throw new NotFoundException(ErrorMessages.CreatorNotFoundMessage(creator.Id));
			return _mapper.Map<CreatorResponseDto>(updatedCreator);
		}

		public async Task DeleteCreatorAsync(long id)
		{
			if (!await _creatorRepository.DeleteAsync(id))
			{
				throw new NotFoundException(ErrorMessages.CreatorNotFoundMessage(id));
			}
		}
	}
}
