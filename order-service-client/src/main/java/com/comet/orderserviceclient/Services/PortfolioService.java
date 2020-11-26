package com.comet.orderserviceclient.Services;

import com.comet.orderserviceclient.Daos.PortfolioDao;
import com.comet.orderserviceclient.Dtos.Portfolio;
import com.comet.orderserviceclient.Dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PortfolioService {

    @Autowired
    PortfolioDao portfolioDao;

    public Portfolio addPortfolio(Portfolio portfolio){
        return portfolioDao.save(portfolio);
    }

    public void deletePortfolio(Long id){
        portfolioDao.deleteById(id);
    }

    public List<Portfolio> getAllPortfolios (){
        return portfolioDao.findAll();
    }

    public List<Portfolio> getAllPortfoliosById(Long id){return portfolioDao.getAllUserPortfolio(id);}

    public Optional<Portfolio> getPortfolioById(Long id){
        return portfolioDao.findById(id);
    }

    public Portfolio updatePortfoilio(Long id){
        return null;
//        Portfolio portfoliotobeUpdated = new Portfolio();
//        portfolioDao.findById(id)
//                .map( portfolio -> {
//                    portfolio.setName(portfoliotobeUpdated.getName());
//                    return portfolioDao.save(portfoliotobeUpdated);
//                }).orElseGet(() -> {
//                    portfoliotobeUpdated.setU_id_FK();
//                    return portfolioDao.save(portfoliotobeUpdated);
//        });
//        return portfoliotobeUpdated;
    }
}
