/*
 * Decompiled with CFR 0_118.
 */
package com.ocularminds.expad.vao;

import java.io.Serializable;

public class PostCardFiles
implements Serializable {
    private String id;
    private String cards;
    private String accounts;
    private String cardAccounts;
    private String accountBalances;
    private String cardOverrideLimits;
    private String accountOverrideLimits;
    private String statements;
    private String bads;

    public PostCardFiles() {
    }

    public PostCardFiles(String id, String cards, String accounts, String cardAccounts, String accountBalances, String cardOverrideLimits, String accountOverrideLimits, String statements) {
        this.setId(id);
        this.setCards(cards);
        this.setAccounts(accounts);
        this.setCardAccounts(cardAccounts);
        this.setAccountBalances(accountBalances);
        this.setCardOverrideLimits(cardOverrideLimits);
        this.setAccountOverrideLimits(accountOverrideLimits);
        this.setStatements(statements);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public void setCardAccounts(String cardAccounts) {
        this.cardAccounts = cardAccounts;
    }

    public void setAccountBalances(String accountBalances) {
        this.accountBalances = accountBalances;
    }

    public void setCardOverrideLimits(String cardOverrideLimits) {
        this.cardOverrideLimits = cardOverrideLimits;
    }

    public void setAccountOverrideLimits(String accountOverrideLimits) {
        this.accountOverrideLimits = accountOverrideLimits;
    }

    public String getId() {
        return this.id;
    }

    public String getCards() {
        return this.cards;
    }

    public String getAccounts() {
        return this.accounts;
    }

    public String getCardAccounts() {
        return this.cardAccounts;
    }

    public String getAccountBalances() {
        return this.accountBalances;
    }

    public String getCardOverrideLimits() {
        return this.cardOverrideLimits;
    }

    public String getAccountOverrideLimits() {
        return this.accountOverrideLimits;
    }

    public void setStatements(String statements) {
        this.statements = statements;
    }

    public String getStatements() {
        return this.statements;
    }

    public void setBads(String bads) {
        this.bads = bads;
    }

    public String getBads() {
        return this.bads;
    }
}

