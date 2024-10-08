package com.sparta.mypet.domain.oauth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.mypet.domain.auth.entity.User;
import com.sparta.mypet.domain.oauth.entity.SocialAccount;
import com.sparta.mypet.domain.oauth.entity.SocialType;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
	Optional<SocialAccount> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

	Optional<SocialAccount> findBySocialTypeAndEmail(SocialType socialType, String email);

	Optional<SocialAccount> findBySocialTypeAndUser(SocialType socialType, User user);

	boolean existsSocialAccountByEmail(String email);

}
