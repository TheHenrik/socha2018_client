package sc.player2018.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sc.plugin2018.Advance;
import sc.plugin2018.CardType;
import sc.plugin2018.FieldType;
import sc.plugin2018.GameState;
import sc.plugin2018.Move;
import sc.plugin2018.Player;
import sc.plugin2018.util.GameRuleLogic;

public class MidGameLogic {
	/*
	 * Plz do´nt kritisize mai inglish. Is there an easy way to get the distance
	 * of a move, or do i have to copy the board and execute the move?
	 * Software-Challenge-GUI is not workiing, so i cant test this programm, it would frustrate me anyway.
	 * At this point i ignored carrot costs. We have enough carrots in this phase.
	 * 
	 */

	public static Move getTurn(GameState gameState) {
		Player currentPlayer = gameState.getCurrentPlayer();
		int currentIndex = currentPlayer.getFieldIndex();
		ArrayList<Move> possibleMoves = gameState.getPossibleMoves();

		if (currentIndex == 22) {
			Move returnMove = getChanceOfFallback(possibleMoves, gameState, currentIndex);
			if (returnMove != null) {
				return returnMove;
			}
		} else if (/*                          //if Movedistance is shorter than the distance to the next saladfield
					 * getRelativSaladPos(possibleMoves, gameState, currentIndex)
					 */ true) {
			Move returnMove = getNearestPosMove(possibleMoves, gameState, currentIndex);
			if (returnMove != null) {
				return returnMove;
			}
		} else { // just if we haven´t returned a Move yet and can go to the next salad field
			MoveList baseList = new MoveList(possibleMoves, gameState);
			// if we can eat a salad, we should
			Move returnMove = baseList.getSaladEat();

			if (returnMove != null) {
				return returnMove;
			}
		}

		return null;
	}

	private static Move getNearestPosMove(ArrayList<Move> possibleMoves, GameState gameState, int currentIndex) {
		List<Move> advanceMoves = new ArrayList<>();
		int currentEnemyPos = gameState.getOtherPlayer().getFieldIndex();
		int furthestEnemyPos = GameRuleLogic.calculateMoveableFields(gameState.getOtherPlayer().getCarrots())
				+ currentEnemyPos;

		for (Move move : possibleMoves) {
			Advance advance = LogicHelper.getAdvance(move);
			if (advance != null) {
				int destination = currentIndex + advance.getDistance();
				if (gameState.getTypeAt(destination) == FieldType.POSITION_1) {
					if (destination >= furthestEnemyPos) {
						advanceMoves.add(move);
					}
				} else if (gameState.getTypeAt(destination) == FieldType.POSITION_2) {
					if (destination < currentEnemyPos) {
						advanceMoves.add(move);
					}
				}
			}
		}

		if (advanceMoves.size() > 0) {
			Collections.sort(advanceMoves, LogicHelper.lowestDistanceComparator);
			return advanceMoves.get(0);
		}

		return null;
	}

	private static Move getChanceOfFallback(ArrayList<Move> possibleMoves, GameState gameState, int currentIndex) {
		int currentEnemyPos = gameState.getOtherPlayer().getFieldIndex();
		int furthestEnemyPos = GameRuleLogic.calculateMoveableFields(gameState.getOtherPlayer().getCarrots())
				+ currentEnemyPos;

		if (currentIndex == 22
				&& (furthestEnemyPos < 22 || getNearestPosMove(possibleMoves, gameState, currentIndex) == null)) {
			MoveList baseList = new MoveList(possibleMoves, gameState);
			Move returnMove = baseList.getFallback();

			if (returnMove != null) {
				return returnMove;
			}
		}

		return null;
	}

	/*																			//i donno how to get fieldindex of the destination of a move
	 * private static boolean getRelativSaladPos(ArrayList<Move> possibleMoves,
	 * GameState gameState, int currentIndex) { if
	 * (gameState.getCurrentPlayer().getSalads() == 0) { return false; }
	 * List<Move> advanceMoves = new ArrayList<>(); int currentEnemyPos =
	 * gameState.getOtherPlayer().getFieldIndex(); Move move =
	 * getNearestPosMove(possibleMoves, gameState, currentIndex); if
	 * (gameState.getNextFieldByType(FieldType.SALAD,
	 * gameState.getCurrentPlayer().getFieldIndex()) >= getMovePos(
	 * getNearestPosMove(possibleMoves, gameState, currentIndex))) { return
	 * true; } return false; }
	 */

}






































// had fun reading that? ;)
