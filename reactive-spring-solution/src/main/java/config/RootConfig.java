package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import accounts.AccountManager;
import accounts.internal.JpaAccountManager;
import rewards.DiningReader;
import rewards.RewardNetwork;
import rewards.internal.GenerateDiningEvents;
import rewards.internal.RewardNetworkImpl;
import rewards.internal.account.AccountRepository;
import rewards.internal.restaurant.RestaurantRepository;
import rewards.internal.reward.RewardRepository;
import rewards.web.RewardsController;

/**
 * Imports Rewards application from rewards-db project.
 */
@Configuration
@Import({ AppConfig.class, DbConfig.class })
@EnableTransactionManagement
public class RootConfig {

	/**
	 * A service for accessing Account information.
	 * 
	 * @return The new account-manager instance.
	 */
	@Bean
	public AccountManager accountManager() {
		return new JpaAccountManager();
	}

	@Bean
	public RewardNetwork rewardNetwork(AccountRepository accountRepository, RestaurantRepository restaurantRepository,
			RewardRepository rewardRepository) {
		return new RewardNetworkImpl(accountRepository, restaurantRepository, rewardRepository);
	}

	@Bean
	RewardsController rewardsController(RewardNetwork rewardNetwork) {
		return new RewardsController(rewardNetwork, testDiningReader());
	}

	@Bean
	public DiningReader testDiningReader() {
		return new GenerateDiningEvents();

	}
}
