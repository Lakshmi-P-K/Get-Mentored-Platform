package com.nineleaps.authentication.jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserStatsDTO {

    private Long totalUsers;
    private Long menteesCount;
    private Long mentorsCount;

    public UserStatsDTO(Long totalUsers, Long menteesCount, Long mentorsCount) {
        this.totalUsers = totalUsers;
        this.menteesCount = menteesCount;
        this.mentorsCount = mentorsCount;
    }

	public Long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Long getMenteesCount() {
		return menteesCount;
	}

	public void setMenteesCount(Long menteesCount) {
		this.menteesCount = menteesCount;
	}

	public Long getMentorsCount() {
		return mentorsCount;
	}

	public void setMentorsCount(Long mentorsCount) {
		this.mentorsCount = mentorsCount;
	}
    

}

