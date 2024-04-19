package com.cw.services;

import com.cw.dao.ParametroAlertaDAO;
import com.cw.models.Empresa;
import com.cw.models.ParametroAlerta;

public class InserirParametrosConfig {
    public void inserirParametrosConfig (Empresa e, ParametroAlerta t) {
        ParametroAlertaDAO parametroAlertaDAO = new ParametroAlertaDAO();

        parametroAlertaDAO.inserirParametroAlerta(e, t);
    }
}
