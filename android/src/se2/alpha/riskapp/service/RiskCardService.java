package se2.alpha.riskapp.service;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import se2.alpha.riskapp.dol.RiskCard;

public class RiskCardService {
    @Inject
    BackendService backendService;

    @Inject
    public RiskCardService(Context context, BackendService backendService) {
        this.backendService = backendService;
    }

    public interface GetNewRiskCardCallback {
        void onResult(RiskCard riskCard);
        void onError(String error);
    }
    public void getNewRiskCard(String id, GetNewRiskCardCallback getNewRiskCardCallback){
            backendService.getNewRiskCardRequest(id, new BackendService.GetNewRiskCardCallback() {
                @Override
                public void onSuccess(RiskCard response) {
                    getNewRiskCardCallback.onResult(response);
                }

                @Override
                public void onError(String error) {
                    getNewRiskCardCallback.onError(error);
                }
            });
    }

    public interface GetAllRiskCardsByPlayerCallback {
        void onResult(List<RiskCard> riskCards);
        void onError(String error);
    }
    public void getAllRiskCardsByPlayer(String id, GetAllRiskCardsByPlayerCallback getAllRiskCardsByPlayerCallback){
        backendService.getAllRiskCardsByPlayerRequest(id, new BackendService.GetAllRiskCardsByPlayerCallback() {
            @Override
            public void onSuccess(List<RiskCard> response) {
                getAllRiskCardsByPlayerCallback.onResult(response);
            }

            @Override
            public void onError(String error) {
                getAllRiskCardsByPlayerCallback.onError(error);
            }
        });
    }

    public interface CanPlayerTradeRiskCardsCallback {
        void onResult(boolean canTrade);
        void onError(String error);
    }
    public void canPlayerTradeRiskCards(String id, CanPlayerTradeRiskCardsCallback canPlayerTradeRiskCardsCallback){
        backendService.getCanPlayerTradeRiskCardsRequest(id, new BackendService.CanPlayerTradeRiskCardsCallback() {
            @Override
            public void onSuccess(boolean response) {
                canPlayerTradeRiskCardsCallback.onResult(response);
            }

            @Override
            public void onError(String error) {
                canPlayerTradeRiskCardsCallback.onError(error);
            }
        });
    }

    public interface PlayerTradeRiskCardsCallback {
        void onResult();
        void onError(String error);
    }
    public void playerTradeRiskCards(String id, PlayerTradeRiskCardsCallback canPlayerTradeRiskCardsCallback){
        backendService.getPlayerTradeRiskCardsRequest(id, new BackendService.PlayerTradeRiskCardsCallback() {
            @Override
            public void onSuccess() {
                canPlayerTradeRiskCardsCallback.onResult();
            }

            @Override
            public void onError(String error) {
                canPlayerTradeRiskCardsCallback.onError(error);
            }
        });
    }
}
