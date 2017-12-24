package com.wendergalan.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import jdk.nashorn.internal.parser.TokenType;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoTopo;
    private Random numeroRandomico;
    private BitmapFont fonte;

	//atributos de configuracao
    private int larguraDispositivo;
    private int alturaDispositivo;
    //primeiro estado 0 - > jogo nao iniciado
    //1 - > jogo iniciado
    private int estadoJogo = 0;
    private int pontuacao = 0;


	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private boolean marcouPonto = false;


	@Override
	public void create () {
        batch = new SpriteBatch();
        numeroRandomico = new Random();
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);

        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo /2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = 300;
	}

	@Override
	public void render () {

        deltaTime = Gdx.graphics.getDeltaTime();

        variacao += deltaTime * 10;

        if (variacao > 2) {
            variacao = 0;
        }

	    if (estadoJogo == 0){//Nao Iniciado
	        if (Gdx.input.justTouched()){
	            estadoJogo = 1;
            }
        }else {

            posicaoMovimentoCanoHorizontal -= deltaTime * 300;
            velocidadeQueda++;


            if (Gdx.input.justTouched()) {
                velocidadeQueda = -15;
            }

            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
            }

            //verifica se o cano saiu totalmente da tela
            if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                posicaoMovimentoCanoHorizontal = larguraDispositivo;
                alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 100;
                marcouPonto = false;
            }

            //verifica pontuacao
            if (posicaoMovimentoCanoHorizontal < 120){
                if (!marcouPonto){
                    pontuacao++;
                    marcouPonto = true;
                }

            }

        }

            batch.begin();


            batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
            batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
            batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos/2);
            batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical);
            fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo/2, alturaDispositivo-50);

            batch.end();




	}
	

}
