package pl.camp.it.services;

import pl.camp.it.services.impl.BalanceServiceImpl;

import java.util.List;

public interface IBalanceService {
    List<BalanceServiceImpl.listBalance> getBalanceMonth(int idGroup, String month);
    List<BalanceServiceImpl.listBalance> getBalanceMonth2(int idGroup, String month);
}
