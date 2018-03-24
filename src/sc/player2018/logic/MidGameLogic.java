package sc.player2018.logic;

import java.util.ArrayList;

import sc.plugin2018.CardType;
import sc.plugin2018.FieldType;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;
import sc.plugin2018.Player;

public class MidGameLogic {
	/*
	 * Johannes' strategy is now deleted. Just lose our salads on the Saladfields on Position 22 & 42. After field 42 see EndGameLogic.
	 */

	private static int SALAD_FIELD_2 = 22; // 22 is OUR salad field
	private static int SALAD_FIELD_3 = 42;
	
	public static Move getTurn(GameState gameState) {
		Player currentPlayer = gameState.getCurrentPlayer();
		Player opponentPlayer = gameState.getOtherPlayer();
		int currentIndex = currentPlayer.getFieldIndex();
		int opponentIndex = opponentPlayer.getFieldIndex();
		ArrayList<Move> possibleMoves = gameState.getPossibleMoves();
		MoveList baseList = new MoveList(possibleMoves,gameState);
		// if we can eat a salad, we should
		Move returnMove = baseList.getSaladEat();
		
		if(returnMove != null) {
			return returnMove;
		}						//strategy before SALAD_FIELD_2 if we are 2nd
		if (opponentIndex > currentIndex && currentIndex < SALAD_FIELD_2){
			if(gameState.getNextFieldByType(FieldType.POSITION_2, currentIndex) < SALAD_FIELD_2){	
			returnMove = baseList.select(FieldType.POSITION_2).getNearest();
				if(returnMove != null) {
					return returnMove;
				}
			}
			else if(gameState.getNextFieldByType(FieldType.POSITION_2, currentIndex) > SALAD_FIELD_2){	
				if(!gameState.isOccupied(SALAD_FIELD_2)){
				returnMove = baseList.select(FieldType.SALAD).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}
				else{
					returnMove = baseList.select(FieldType.HEDGEHOG).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}
				}
								//strategy before SALAD_FIELD_2 if we are 1st
		if (currentIndex > opponentIndex && currentIndex < SALAD_FIELD_2){
			if(gameState.getNextFieldByType(FieldType.POSITION_1, currentIndex) < SALAD_FIELD_2){	
				returnMove = baseList.select(FieldType.POSITION_1).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}

			}
			else if(gameState.getNextFieldByType(FieldType.POSITION_1, currentIndex) > SALAD_FIELD_2){
				returnMove = baseList.select(FieldType.SALAD).getNearest();
			}

		}
									//strategy before SALAD_FIELD_3 if we are 1st
		if (currentIndex > opponentIndex && currentIndex < SALAD_FIELD_3){
			if(gameState.getNextFieldByType(FieldType.POSITION_1, currentIndex) < SALAD_FIELD_3 ){	
				returnMove = baseList.select(FieldType.POSITION_1).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}

			
			else if(gameState.getNextFieldByType(FieldType.POSITION_1, currentIndex) > SALAD_FIELD_2){
				returnMove = baseList.select(FieldType.SALAD).getNearest();
			}
		}
									//strategy before SALAD_FIELD_3 if we are 2nd
		if (currentIndex < opponentIndex && currentIndex < SALAD_FIELD_3){
			if(gameState.getNextFieldByType(FieldType.POSITION_2, currentIndex) < SALAD_FIELD_3){	
				returnMove = baseList.select(FieldType.POSITION_2).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}
				else if(gameState.getNextFieldByType(FieldType.POSITION_2, currentIndex) > SALAD_FIELD_3){	
					if(!gameState.isOccupied(SALAD_FIELD_3)){
					returnMove = baseList.select(FieldType.SALAD).getNearest();
						if(returnMove != null) {
							return returnMove;
						}
					}
					else{
						returnMove = baseList.select(FieldType.CARROT).getNearest();
						if(returnMove != null) {
							return returnMove;
						}
					}
					}
		}
		/*if (currentIndex >= 22) {
			// go back, you have salads to eat!
			returnMove = baseList.getFallback();
			if(returnMove != null) {
				return returnMove;
			}
		} else {
			if (!gameState.isOccupied(SALAD_FIELD)) {
				returnMove = baseList.select(FieldType.SALAD).getNearest();
				if(returnMove != null) {
					return returnMove;
				}
				returnMove = baseList.select(CardType.EAT_SALAD).getNearest();
				if(returnMove != null) {
					return returnMove;
				}
			} else {
				if (currentIndex == FALLBACK_FIELD) {
					returnMove = baseList.select(FieldType.POSITION_2).getNearest();
					if(returnMove != null) {
						return returnMove;
					}
				}
				returnMove = baseList.getFallback();
				if(returnMove != null) {
					return returnMove;
				}
				returnMove = baseList.getNearest();
				if(returnMove != null) {
					return returnMove;
				}
			}
		}*/
		return null;
	}
}
