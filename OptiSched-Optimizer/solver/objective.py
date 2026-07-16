from dataclasses import dataclass

from highspy import Highs

from .variables import AuxiliaryVariables

from mapper import SolverData

def build_objective(model: Highs, auxiliary: AuxiliaryVariables, data: SolverData) -> None:

    weights = data.objective_weights

    # ======================================================
    # S1 - Timetable Holes
    #
    # α ( Σl - Σf - Σz )
    #
    # Constant terms are omitted.
    # ======================================================

    for column in auxiliary.l.values():
        model.changeColCost(column, weights.alpha)

    for column in auxiliary.f.values():
        model.changeColCost(column, -weights.alpha)

    for column in auxiliary.z.values():
        model.changeColCost(column, -weights.alpha)

    # ======================================================
    # S2 - Distribution Along the Week
    #
    # β Σv
    # ======================================================

    for column in auxiliary.v.values():
        model.changeColCost(column, weights.beta)

    # ======================================================
    # S3 - Excessive Consecutive Lectures
    #
    # γ Σg
    # ======================================================

    for column in auxiliary.g.values():
        model.changeColCost(column, weights.gamma)

    # ======================================================
    # S4 - Classroom Stability
    #
    # δ Σu
    #
    # The constant (-|O|) is omitted.
    # ======================================================

    for column in auxiliary.u.values():
        model.changeColCost(column, weights.delta)